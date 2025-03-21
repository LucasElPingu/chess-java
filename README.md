# PROJETO XADREZ

> Neste documento está registrado anotações e observações que escreverei dos passos que foram seguidos durante a criação do projeto do curso "Curso mais didático e completo de Java e POO, UML, JDBC, JavaFX, Spring Boot, JPA, Hibernate, MySQL, MongoDB e muito mais" do Professor Nélio Alves na Udemy.
------
# UML:

## PRIMEIRA CAMADA
![Captura de tela 2025-03-18 130348](https://github.com/user-attachments/assets/3095a035-30b8-4a64-a32f-109e1c7adfc7)
## SEGUNDA CAMADA
![Captura de tela 2025-03-18 130401](https://github.com/user-attachments/assets/3ab82038-d019-4ded-bb76-c1c4c73bea54)

-------

## Criando o projeto e usando o o git para o push

### Checklist:
- **Github:** Criar um novo repositório
  - **Nota:** Escolher .gitignore do tipo Java

### Passos:
1. Abrir um terminal na pasta do projeto e executar os seguintes comandos:
   ```bash
   git init
   git remote add origin https://github.com/acenelio/chess-system-java.git
   git pull origin master
   git add .
   git commit -m "Project created"
   git push -u origin master
   ```
------
## Criando classes Board e Piece

### Checklist:

- **Classes:** `Piece`, `Board` [public]
- **Tópicos de POO:**
  - Associações
  - Construtores (Passar apenas o `board`, pois a posição de uma peça recém-criada será inicialmente nula)
  - Encapsulamento / Modificadores de acesso
  - **Obs:** O atributo `position` é `protected` (simbolizado no UML por `#`), pois ele representa uma posição simples de matriz, não a posição do xadrez. O `setBoard` é removido, enquanto `getBoard` é `protected` e `position` não possui `get` e `set`.
- **Tópicos de Estruturas de Dados:**
  - Matriz (Possui uma matriz do tipo `Piece`)

------

## Camada de Xadrez e imprimindo o tabuleiro

### Representação inicial do tabuleiro:

```
8 - - - - - - - -
7 - - - - - - - -
6 - - - - - - - -
5 - - - - - - - -
4 - - - - - - - -
3 - - - - - - - -
2 - - - - - - - -
1 - - - - - - - -
  a b c d e f g h
```

### Checklist:

- **Métodos:** `Board.Piece(row, column)` e `Board.Piece(position)`
- **Pacote:** `chess`
- **Enum:** `Chess.Color` (`BLACK` e `WHITE`)
- **Classes:**
  - `Chess.ChessPiece` [public] (A cor da peça não pode ser modificada)
  - `Chess.ChessMatch` [public] (Gerencia a dimensão do tabuleiro e a posição das peças)
  - `ChessConsole.UI` (Classe para imprimir o tabuleiro com as peças)
- **Tópicos de POO:**
  - Enumerações (Cores)
  - Encapsulamento / Modificadores de acesso
  - Herança (ChessPiece herdade de Piece)
  - Downcasting (E feito um downcasting nas Piecas para ChessPiece, pois as ChessPiece que deve ser acessada)
  - Membros estáticos (Na classe UI para ser acessado sem a necessidade de ser instanciado)
  - Padrão de camadas (Layers pattern)
- **Tópicos de Estruturas de Dados:**
  - Matriz
------
## Criação de exceções personalizadas e programação defensiva

### Checklist:
- Class `BoardException` [public] (Exceção para o tabuleiro)
- Methods:
  - `Board.PositionExists`
  - `Board.ThereIsAPiece` (Verifica se existe a posição e se nessa posição tem alguma peça)
- Implementar programação defensiva nos métodos de `Board`

### **OOP Topics:**
- Exceptions
- Constructors (a string deve ser informada à exceção)

------

## ChessException e ChessPosition (Exceção na camada de xadrez)

### Checklist:
- Class `ChessException` [public]
- Class `ChessPosition` [public] (Classe que representa o sistema de coordenadas do tabuleiro, ex: "a1", possui os métodos para converter de uma posição de xadrez para uma posição comum e o inverso)
  - OBS: Se subtrair o Unicode do caractere `'a' - 'a' = 0`, `'b' - 'a' = 1` e assim por diante.
- Refatorar `ChessMatch.InitialSetup`

### **OOP Topics:**
- Exceptions
- Encapsulation
- Constructors (a string deve ser informada à exceção)
- Overriding
- Static members
- Layers pattern

------

## Melhora na impressão do tabuleiro

### Color in terminal:
- **Windows:** Git Bash (na pasta `bin` onde ficam os arquivos compilados do projeto, dentro do `mavenproject` e na pasta `\target\classes`)
- **Mac:** Pesquisar "osx terminal color"
  - OBS: O projeto será executado pelo Git Bash

### Checklist:
- Colocar mais peças no tabuleiro
- Distinguir cores das peças no método `UI.PrintPiece`

------

## Movendo as peças

### Checklist:
- Method `Board.RemovePiece` (método para remover a peça)
- Method `UI.ReadChessPosition` (método para ler uma posição do usuário quando ele mover a peça)
- Method `ChessMatch.PerformChessMove`
  - Converte de xadrez para matriz
  - Valida a posição
  - Movimenta a peça
  - Retorna a peça capturada
  - Method `ChessMatch.MakeMove` (Faz o movimento da peça removendo uma possível peça na posição de destino)
  - Method `ChessMatch.ValidadeSourcePosition` (Mudar a `RunTimeException` na classe `ChessException` para `BoardException`, pois exceções de xadrez são também exceções de tabuleiro)
- Escrever a lógica básica no `Program.cs` (Fazer a leitura e o movimento da peça)

### **OOP Topics:**
- Exceptions
- Encapsulation

------

## Lidando com as exceções e limpando a tela

### Clear screen using Java:
```java
// https://stackoverflow.com/questions/2979383/java-clear-the-console
public static void clearScreen() {
 System.out.print("\033[H\033[2J");
 System.out.flush();
}
```

### Checklist:
- `ChessException`
- `InputMismatchException`
- 
------

## Possíveis movimentos de uma peça

### Checklist:
- **Métodos na classe `Piece`**
  - `PossibleMoves` [abstract] (Retorna uma matriz de boolean indicando os movimentos possíveis de cada peça)
  - `PossibleMove` (Verifica se a peça pode se mover para uma posição específica)
  - `IsThereAnyPossibleMove` (Verifica se há pelo menos um movimento possível)
- **Implementação básica de `PossibleMove` para `Rook` e `King`**
- **Atualização de `ChessMatch.ValidateSourcePosition` (Verificação de ao menos um movimento disponível para a peça)**
- **Tópicos de POO:**
  - Métodos e classes abstratas
  - Exceções

---

## Implementação dos movimentos da torre

### Checklist:
- **Método `ChessPiece.IsThereOpponentPiece(position)` [protected]** (Verifica se há peças inimigas na posição de destino)
- **Implementação de `Rook.PossibleMoves`** (Determina as casas para onde a torre pode se mover)
- **Método `ChessMatch.ValidateTargetPosition`** (Valida se a posição de destino é válida em relação à de origem)
- **Tópicos de POO:**
  - Polimorfismo
  - Encapsulamento / Modificadores de acesso [`protected`]
  - Exceções
    
------

## Imprimindo possíveis movimentos
### Checklist:
- **Method ChessMatch.PossibleMoves**: Retorna uma matriz de boolean com os possíveis movimentos a partir da peça.
- **Method UI.PrintBoard [overload]**: Se for passada uma matriz de booleans como argumento no método `printBoard`, ele passará a matriz no argumento do `printPieces`; caso contrário, ele passará `false`.
- **Refactor main program logic**: No `Program`, criar uma matriz de booleans recebendo `possibleMoves`, limpar a tela e depois passar como argumento no `printBoard`.

### OOP Topics:
- Overloading

------

## Implementando os possíveis movimentos básicos do rei
### Checklist:
- **Method King.CanMove(position) [private]**: Retorna se o rei pode mover para uma determinada posição, armazena a peça na posição passada em `p` e retorna `true` se a peça for `null` ou a cor for diferente.
- **Implement King.PossibleMoves**: Implementação dos movimentos básicos do rei (exemplo: verificar a posição `row - 1, column` para movimento para cima).

### OOP Topics:
- Encapsulation
- Polymorphism

------

## Trocando o turno de cada jogador
### Checklist:
- **Class ChessMatch:**
  - **Properties Turn, CurrentPlayer [private set]**
  - **Method NextTurn [private]**: Incrementa o `int turn` e troca a cor armazenada em `currentPlayer`.
  - **Update PerformChessMove**: Adiciona `nextTurn` antes do `return`.
  - **Update ValidadeSourcePosition**: Verifica se a peça escolhida pertence ao `currentPlayer`; caso contrário, lança uma exceção.
- **Method UI.PrintMatch**: Imprime o tabuleiro e depois exibe o turno e a cor do jogador.

### OOP Topics:
- Encapsulation
- Exceptions

------

## Lidando com as peças capturadas
### Checklist:
- **Method UI.PrintCapturedPieces**: Cria duas listas para armazenar as peças capturadas (pretas e brancas).
- **Update UI.PrintMatch**: Adiciona a exibição das peças capturadas ao lado do tabuleiro.
- **Update Program logic**: Adiciona um `if` para verificar se uma peça foi capturada e armazená-la na lista de peças capturadas.
- **Lists in ChessMatch:**
  - `_piecesOnTheBoard`, `_capturedPieces`
  - **Update constructor**: Instancia as listas no construtor.
  - **Update PlaceNewPiece**: Adiciona as peças à lista de peças no tabuleiro.
  - **Update MakeMove**: Remove a peça da lista do tabuleiro e adiciona à lista de peças capturadas se necessário.

### OOP Topics:
- Encapsulation
- Constructors

### Data Structures Topics:
- List

------

## Implementação da lógica de check
### Regras:
- `Check` significa que o rei está sob ameaça de pelo menos uma peça adversária.
- O jogador não pode se colocar em `check`.

### Checklist:
- **Property ChessPiece.ChessPosition [get]**: Cria um método `getChessPosition` para retornar a posição no formato do tabuleiro.
- **Class ChessMatch:**
  - **Method UndoMove**: Desfaz a última jogada.
  - **Property Check [private set]**: Booleano `check`.
  - **Method Opponent [private]**: Retorna a cor do oponente com base na cor do jogador.
  - **Method King(color) [private]**: Obtém a posição do rei da cor especificada.
  - **Method TestCheck**: Verifica se o rei está em `check` percorrendo a lista de peças adversárias e verificando se alguma pode se mover para a posição do rei.
  - **Update PerformChessMove**:
    - Testa se o movimento coloca o próprio rei em `check`; caso positivo, desfaz a jogada e lança uma exceção.
    - Testa se o oponente está em `check` e ajusta a variável `check`.
- **Update UI.PrintMatch**: Exibe a mensagem `CHECK!` caso o rei esteja sob ameaça.

------

## Implementação da lógica de checkmate
### Checklist:
- **Class ChessMatch:** 
  - **Property Checkmate [private set]** : boolean que confirmara o cheque-mate.
  - **Method TestCheckmate [private]**: Lógica de cheque-mate.
  - **Update PerformChessMove**: Testa se o oponente da peça que se mexeu está em check-mate e seta a variável **checkMate** para **true**, além de colocar no **else** o **nextTurn**.
- **Update UI.PrintMatch**: Antes de permitir que o jogador jogue, verifica se não foi cheque-mate, caso seja falso entra no **if** e permite o jogador continuar, **else** imprime cheque-mate.
- **Update Program logic**: Coloca no enquanto não for cheque-mate... No final limpa a tela e imprimi o **printMatch**

------
------
------
------
------
------
------
------
------
------
------
------
------
------
------
