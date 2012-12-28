## Introduction

A program for brute-forcing a solution to this puzzle:

https://spinpossible.com/play/sandbox.pl?shareid=th3&puzzleid=423159786s5

(This program doesn't find a solution in 5 moves, so unless I goofed,
there really isn't one)

To start brute-forcing, run:

mvn clean install
mvn exec:java -Dexec.mainClass=spinpossible.Spinpossible
