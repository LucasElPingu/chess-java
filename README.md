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
------
