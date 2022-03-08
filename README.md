# jdbcdao - Niklas Heim

Mitschrift fuer den uebungszettel `ÜBUNGSZETTEL „DATENBANKZUGRIFF MIT JAVA, JDBC UND DAO“`.

## JDBC INTRO TEIL 1
### Entwicklunsumgebung & Projekt

In VS Code wird ein neues Maven Projekt angelegt. Es wird eine Dependency `mysql-connector-java` eingefuegt, die die Kommunikation zu unserer MySQL Datenbank erlaubt.

### Datenbank

In `phpmyadmin` wird eine neue Datenbank `jdbcdemo` angelegt. In dieser Datenbank wird nun eine Tabelle `student` erstellt. Diese enthaelt drei Spalten: `id`, `name` und `email`. Die Spalte `id` stellt in dieser Tabelle den Primaerschluessel dar und wird automatisch erhoeht.

### Verbindung zur Datenbank

Nun kann eine Verbindung zur Datenbank hergestellt werden. Es werden Variablen mit den Zugangsdaten zur Datenbank erstellt. Ueber `DriverManager.getConnection(connectionUrl, connectionUser, connectionPass)` wird die Verbindung iniziiert. Dies geschieht in einem Try-Catch-Block, um bei einem Fehler reagieren zu koennen (z. B. Falscher Port, falsches Passwort oder Datenbank nicht vorhadnen.).

```java
String selectAllStudents = "SELECT * FROM `student`";
String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
String connectionUser = "root";
String connectionPass = null;

try {
    Connection conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPass);
    System.out.println("Connection successful");
} catch (SQLException e) {
    System.out.println("Connection error: " + e.getMessage());
}
```

### Prepared-Statement für die Abfrage von Daten aus der DB

Da jetzt eine Verbindung zur Datenbank moeglich ist, koennen wir Daten aus dieser auslesen. Im ersten Schritt wird ein Statement geschrieben, dass uns alle Studenten aus der `student` Tabelle liefert. Aus diesem Statement wird nun ein `PreparedStatement` Objekt ertstellt. Beim Ausfuehren der `executeQuery()` Methode diesed Objekts erhalten wir ein `ResultSet` mit unseren Daten.

```java
String selectAllStudents = "SELECT * FROM `student`";
PreparedStatement preparedStatement = conn.prepareStatement(selectAllStudents);
ResultSet rs = preparedStatement.executeQuery();
```

#### Ausgabe der ermittelten Datensaetze

Die erhaltenen Datensaetze sollen nun in der Konsole ausgegeben werden. Hierzu verwenden wir eine `while` Schleife. Durch `rs.next()` wird ueberprueft, ob ein naechster Eintrag im `ResultSet` vorhanden ist. Ist keiner vorhanden, liefert die Methode den boolschen Wert 0 und die Schleife wird beendet. Ist ein weiterer Eintrag vorhanden, wird dieser in der naechsten Wiederholung verwendet.

Die einzelnen Spalten eines Datensatzes werden ueber `getInt()`, `getString()` oder weiteres mittels der Spaltenzahl oder des Spaltennames ausgelesen.

```java
while (rs.next()) {
    int id = rs.getInt("id");
    String name = rs.getString("name");
    String email = rs.getString("email");

    System.out.printf("Student aus der DB: [ID] %d, [Name] %s, [E-Mail] %s.\n", id, name, email);
}
```

### Prepared-Statement für die Erstellung von Daten in der DB

Es koennen auch Datensaetze in die Datenbank eingefuehrt werden. Hierzu muss das Statement angepasst werden. Es wird nun ein Insert-Statement verwendet. Die eingefuegten Werte werden hier mit dem `?` vorerst dargestellt. Es wird wieder ein `PreparedStatement` erstellt. Ueber die Methode `setString()` werden nun die Daten eingefuegt. Das Befuellen mit Daten und Ausfuehren des Statements ueber `executeUpdate()` wird nun in einem eigenen Try-Catch-Block erledigt, um auf Fehler des Statements reagieren zu koennen.

> Druch das `PreparedStatment` Objekt wird das Statement bereits vorkompiliert. Dadurch koennen Abfragen schneller bearbeitet werden und es wird verhindert, dass SQL-Statements vom User eingeschleust werden (SQL-Injection).

```java
String insertStudent = "INSERT INTO `student`  (`id`, `name`, `email`) VALUES (null, ?, ?)";
PreparedStatement preparedStatement = conn.prepareStatement(insertStudent);

try {
    preparedStatement.setString(1, "Michael Bogensberger");
    preparedStatement.setString(2, "mibog@tsn.at");
    int rowAffected = preparedStatement.executeUpdate();
} catch (SQLException e) {
    System.out.println("Fehler im SQL-Insert Statement: " + e.getMessage());
}
```
![Eingefuegter Datensatz](https://i.imgur.com/nNZCcrr.png)

### Prepared-Statement für die Aenderung von Daten in der DB

Es koennen auch Daten in der Datenbank geaendert werden. Hierbei wird ein `UPDATE` Statement verwendet. Es wird die `id` des zu aendernden Datensatzes verwendet und die Datenfelder, die geaendert werden sollten. Der Rest ist vergleichbar mit einem `INSERT` Statement. Hier wird der `name` des Studenten mit der `id` 2 auf "Meili" gesetzt.

```java
String updateStudent = "UPDATE `student` SET `name` = ? WHERE `student`.`id` = 2";
preparedStatement.setString(1, "Meili");
int rowAffected = preparedStatement.executeUpdate();
```

### Prepared-Statement für das Loeschen von Daten in der DB

Das Loeschen von Datensaetzen wird durch ein `DELETE` Statement ermoeglicht. Es wird die Tabelle und die `id` des zu loeschenden Studenten angegeben.

```java
String deleteStudent = "DELETE FROM `student`WHERE `student`.`id` = 3";
```
![Geloeschter Datensatz](https://i.imgur.com/3f8Y8oO.png)

### Prepared-Statement für die Abfrage mit Filter von Daten aus der DB

Beim Auslesen von Datensaetzen koennen diese auch gefiltert werdem. Hierbei wird gesucht, ob der `name` ein gewisses Pattern aufweist oder nicht. Nur Datensaetze die dem Filter entsprechen werden von der Datenbank zurueckgegeben.

```java
String selectAllStudents = "SELECT * FROM `student` WHERE `student`.`name` LIKE ?";
preparedStatement.setString(1, "%" + pattern + "%");
```