# motoforum

## Technologie und Kurzbeschreibung

Dieses Repo beinhaltet eine Motoforum Applikation in welcher man sich gegenseitig austauschen kann. Das Backend / die API benutzt Springboot. Im Frontend wird ReactJS verwendet, um unter anderem API-Calls zu machen und diese Daten danach anzuzeigen.

## Installationsschritte

- Clone git repository
- Repository in preferierten IDE öffnen
- npm modules installieren (`npm install`)
- docker compose ausführen
- Springboot Application starten
- React Frontend starten mit (`npm start`)

## Grade relevant content / Notenrelevante Inhalte

### This part is written in german and is relevant for grading this school project

#### User Stories und Akzeptanzkriterien

1. Ich als Benutzer möchte einen Account anlegen sodass mich die anderen Benutzer identifizieren können.
    - Der Registrierungsprozess muss einfach und benutzerfreundlich sein.
    - Nach der Registrierung sollte der Benutzer Zugriff auf sein Profil haben, um es bei Bedarf zu bearbeiten.
---
2. Ich als Benutzer möchte public Threads erstellen um Fragen zu fragen oder Erlebnisse zu teilen.
    - Das Erstellen eines Threads sollte leicht verständlich sein.
    - Benutzer sollten in der Lage sein, verschiedene Kategorien für ihre Threads auszuwählen.
---
3. Ich als Benutzer möchte durch beantworten von Fragen oder mitschreiben im Forum meine Reputation erhöhen.
    - Das beantworten von Fragen sollte leicht gehen.
    - Reputationssystem funktioniert und ist nicht zu kompliziert gestaltet.
---
4. Ich als Benutzer möchte meine Threads verwalten um sie allenfalls zu schliessen oder zu löschen.
    - Benutzer sollten die Möglichkeit haben, ihre eigenen Threads zu bearbeiten, zu schließen oder zu löschen.
    - Es sollte eine Bestätigungsnachricht geben, bevor ein Thread dauerhaft gelöscht wird, um versehentliche Löschungen zu vermeiden.
---
5. Ich als Gast möchte auf public Threads zugreiffen um mich zu informieren.
    - Öffentliche Threads sollten für Gäste zugänglich sein, ohne dass eine Anmeldung erforderlich ist.
    - Die Navigation durch die Threads sollte einfach und benutzerfreundlich sein.
---
6. Ich als Gast möchte auf public Threads zugreiffen und allenfalls meine Inputs dazugeben.
    - Gäste sollten die Möglichkeit haben, auf Threads zu antworten oder Kommentare hinzuzufügen, ohne sich anmelden zu müssen.
    - Es sollte jedoch eine Captcha- oder ähnliche Sicherheitsmaßnahme geben, um Missbrauch zu verhindern.
---
7. Ich als Admin möchte Threads welche nicht zugelassene Inhalte (Hauptfrage, Antworten) haben, löschen.
    - Administratoren sollten die Möglichkeit haben, Threads zu überprüfen und bei Bedarf unzulässige Inhalte zu entfernen.
    - Es sollte klare Richtlinien geben, was als unzulässiger Inhalt betrachtet wird.
---
8. Ich als Admin möchte Benutzer welche mehrheitlich schlecht auffallen, von dem Forum bannen.
    - Administratoren sollten in der Lage sein, Benutzerkonten zu sperren oder zu löschen, die gegen die Community-Richtlinien verstoßen.
    - Die Entscheidung über das Verbannen eines Benutzers sollte nach klaren Kriterien und unter Berücksichtigung der Community-Richtlinien getroffen werden.

#### Transactions / Transaktionen

Ich werde in meinem Projekt transaktionen benutzen. Diese werden in folgenden Controllern benutzt:

- AuthController beim **PostMapping("/signup")**
- UserController beim **PutMapping("/{userId}/{valueToUpdate}")**
- UserController beim **DeleteMapping("/delete/{userToDeleteId}")**
- ReplyController beim **PostMapping("/insert")**
- ReplyController beim **DeleteMapping("/delete/{replyId}")**
- QuestionController beim **PostMapping("/ask")**
- QuestionController beim **DeleteMapping("/delete/{questionToDeleteId}")**

#### Sicherheitskonzept

##### Sicherheitskonfiguration

Sämtliche Sicherheitkonfigurationen sind im WebSecurityConfig festgelegt.

##### Autorisierung

Die Autorisierung basiert auf die JWT Tokens. Manche Ressourcen der Anwendung sind geschützt und erfordern einen JWT Token für den Zugriff.

- Ressourcen für jeden Benutzer:
  - `/api/auth/**`: Endpunkte für die Authentifizierung
  - `/public`: Öffentliche Ressourcen
  - `/swagger-ui/**`: Swagger UI für API-Dokumentation

- Geschützte Ressourcen (Nur für eingeloggte user):
  - `/private`: Private Ressourcen
  - `/question/**`: Endpunkte zur Frageverwaltung
  - `/reply/**`: Endpunkte zur Antwortverwaltung
  - `/user/**`: Benutzerverwaltungs-Endpunkte

Der Token wird bei einer Request im frontend mitgesendet und ist im Local Browser Storage gespeichert.

#### Externe Ressourcen

ChatGPT, Schulkollegen