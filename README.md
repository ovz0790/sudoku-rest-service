# sudoku-rest-service
This is simple REST service with Sudoku. Algorithm for solving Sudoku have been taked from http://rosettacode.org/wiki/Sudoku#Java so it's not mine

USING SERVICE:

1) Build .jar file with maven

2) Starting service:
java -jar sudoku.jar

3) Using REST API:

Create and getting new board
POST http://<hostname>:9001/demo/sudoku/init_board

Show created board 
GET http://<hostname>:9001/demo/sudoku/show_task_board

Show board changed by user
GET http://<hostname>:9001/demo/sudoku/show_user_board

Set/reset board element
POST http://<hostname>:9001/demo/sudoku/change_element?xCoord=1&yCoord=2&val=3

Submitting user solution (with/withowt cleaning existing board)
GET http://<hostname>:9001/demo/sudoku/submit
GET http://<hostname>:9001/demo/sudoku/submit?clear=true
