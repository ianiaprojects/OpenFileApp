# OpenFileApp

Program care, prin intermediul unui GUI, permite utilizatorului să
aleagă un fisier/director, pentru care, in funcție de tipul de fisier selectat, să arate diferite
informații într-un JTextArea. Pentru selecția fișierului se poate folosi clasa JFileChooser.
Dacă fisierul selectat este :

Director – afișat dimensiunea directorului in kB și numărul de fisiere din primul nivel
(adică neincluzând fisierele din directoare “interne”)
 *.txt – afișat ultimele 5 linii ale fisierului
 *.zip – afișat o listă cu fisierele și directoarele din arhivă (See ZipFile class)
 *.png, *.jpeg, *.gif, *.bmp – afișat dimensiunea imaginii (See ImageIO class)
