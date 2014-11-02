JCC = javac
JFLAGS = -g

default: Program.class GUI.class 

Program.class: Program.java
	$(JCC) $(JFLAGS) Program.java

GUI.class: GUI.java
	$(JCC) $(JFLAGS) GUI.java
				
clean: 
	$(RM) *.class
