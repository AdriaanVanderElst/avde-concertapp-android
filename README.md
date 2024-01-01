# Concert Planner Applicatie

Deze applicatie kan een uit een beveiligde API opgehaalde lijst van geplande concerten voor een artiest weergeven met alle bijhorende info, en deze lijst bijhouden in lokaal geheugen.
Gebruikers van de app (zoals bandleden, management, boeker, ...) kunnen commentaar toevoegen aan elk individueel concert via de detailpagina (die weergegeven wordt wanneer men op een van de elementen in de lijjst klikt).
Na toevoegen van opmerkingen in het tekstveld kan deze opmerking opgeslagen worden. Wanneer men commentaren heeft toegevoegd kan men in de navigatiebalk de gewijzigde commentaren updaten naar de API door op de "opslaan" knop te drukken.
De app en API zijn beveiligd via Auth0, login is dus noodzakelijk om de app te kunnen gebruiken.
## Documentatie
Documentatie is beschikbaar op: https://adriaanvanderelst.github.io/avde-concertapp-android/

## Login informatie
```
email: admin@concertapp.com
passw: Admin!capp01
```
## Opmerkingen
Omdat de API draait via Render en verbonden is met een Amazon RDS databank, lopen acties die de API aanspreken soms stroef.
