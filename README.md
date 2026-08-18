# Atividade

Que tal refletirmos um pouco sobre o que acontece com esse código quando novos requisitos são adicionados?

---

## 🎯 Objetivo da Atividade

Altere o código da classe PlanetasPrinter que os planetas também possam ser impressos.
Tente fazer isso com o menor esforço possível.

Verifique a classe [br.pucpr.Planet](https://github.com/ViniGodoy/design-patterns/blob/aula-01-ex/src/main/java/br/pucpr/planet/Planet.java)

O formato de impressão da tabela (bordas, temas) deve ser exatamente igual ao da tabela de usuários.

Quanto aos dados:
1. Devem ser impressas as seguintes colunas "Nome", "Diâmetro", "Dist. sol (km)", "Dist. sol (ua)", e "Tipo". Observação uma unidade astronomica (UA) é equivalente à distância da Terra ao Sol.
2. O diametro do planeta deve ser impresso com 1 casa decimal e separadores de milhar.
3. A distância até o sol em km deve ser impressa em um número inteiro com separadores de milhar.  
4. Já a distância em UA deve ser impressa com duas casas decimais com separadores de milhar.
5. O tipo deve ser impresso como "Rochoso", "Gososo", "Gelado", "Anão"

## 📋 Reflexão final

Ao final da implementação reflita:

1. Sua solução ficou com muito código duplicado?
   
Sim, bastante. A estrutura de impressão dos planetas acabou sendo praticamente uma cópia da estrutura usada para os usuários: a montagem da borda, o cabeçalho, o tratamento de lista vazia ou nula, o percurso pelos itens e o bloco de alinhamento à direita são, em essência, os mesmos em ambas as classes. A única parte realmente nova foi a definição de quais colunas existem e como cada dado do planeta deve ser formatado. Ou seja, a "moldura" da tabela se repetiu quase por completo, e só o conteúdo mudou.

2. O que aconteceria se uma terceira classe tivesse que ser adicionada?

O mesmo padrão se repetiria: eu criaria uma nova classe, copiando novamente essa estrutura comum e adaptando apenas as colunas e a formatação dos dados. Isso significa que qualquer decisão sobre a aparência da tabela (a largura da borda, o caractere usado, o comportamento do alinhamento, o tratamento de valores nulos) passaria a existir em três lugares diferentes, quando na verdade é a mesma regra sendo aplicada três vezes. Isso é um risco real: se no futuro for necessário ajustar algo nessa parte comum, será preciso lembrar de alterar os três printers da mesma forma, e basta esquecer um deles para as tabelas começarem a se comportar de maneira inconsistente entre si, sem que isso fique evidente de imediato.
Faria mais sentido que essa parte comum existisse em um único lugar, e que cada nova classe de impressão só precisasse informar o que é particular a ela (as colunas e como formatar seus dados), sem precisar recriar toda a lógica de montagem da tabela a cada nova necessidade.


---

## 🛠️ Requisitos para Execução

* **Linguagem:** Java 17 ou superior

### Como Executar

```bash
javac PlanetasPrinter.java
java PlanetasPrinter
```
