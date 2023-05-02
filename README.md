# 🃏 Briscola Online 🃏

## 📜 Abstract
Il progetto prevede la realizzazione di una versione online multiplayer del famoso gioco di carte Briscola.
Le partite saranno esclusivamente composte da due giocatori, in modalità 1vs1. Le carte utilizzate per giocare a Briscola sono 40, raggruppate in 4 gruppi definiti “semi” (coppe, denari, bastoni, spade). La tipologia di carte sarà quella Romagnola. I punti totali presenti nel mazzo sono 120; per decretare il vincitore è necessario raggiungere un minimo di 61 punti, mentre se i punti raggiunti sono 60, si verifica una situazione di parità.

## 🎯 Goals/Requirements

### 🛰 Connectivity requirements
- All’avvio dell’applicazione l’utente inserirà un nickname.
- A quel punto potrà decidere se creare una lobby di gioco o unirsi a una esistente.
- Quando ci sono due giocatori in una lobby, ha inizio la partita.
- Al termine di questa sarà possibile ri-sfidare l’avversario o uscire dalla lobby.

### 👾 Game's requirements
- A inizio partita vengono distribuite tre carte casuali a giocatore, un’altra carta viene mostrata sul tavolo e il suo seme decreterà il seme di briscola.
- Il primo turno sarà scelto casualmente, dopodiché la giocata spetterà sempre al giocatore che ha vinto la presa precedente.
- Il peso delle carte in ordine crescente è 2-4-5-6-7-fante-cavallo-re-tre-asso. Il seme di briscola ha peso maggiore su tutti gli altri semi. Il giocatore che tira la carta con peso maggiore vince la presa.
- A fine partita si conta il valore totale delle proprie prese, nella seguente maniera.
    - Fante → 2 punti.
    - Cavallo → 3 punti.
    - Re → 4 punti.
    - Tre → 10 punti.
    - Asso → 11 punti.
    - Tutte le altre carte → 0 punti.
- Su un totale di 120 punti, il giocatore che raggiunge almeno 61 vince la partita. In caso di 60 punti esatti è un pareggio.

### 🔍 System's requirements
- Il sistema sarà implementato in Java con un’architettura client-server.
- Il server sarà Web Service. Le librerie utilizzate saranno io.javalin.Javalin e com.google.code.gson per la serializzazione e deserializzazione. Il server gestirà le lobby, la connessione dei client a esso e la logica di gioco.
- Gli utenti usufruiranno di un’interfaccia grafica sviluppata con l’ausilio di JavaFX per giocare.

### ⚙️ Deployment
Per poter giocare a
*Briscola Online* è sufficiente che la macchina sia dotata di una JVM (versione ≥17). Soddisfatto questo semplice requisito, si può lanciare il server tramite il seguente comando (dopo essersi posizionati da terminale all’interno della cartella principale del repository):

``` bash
./briscola-online/gradlew server:run
```

Questo comando lancerà un’istanza del Web Service per *Briscola Online*
di default su *http://localhost:7777*. Si può specificare una porta differente come
argomento al comando precedente.

Per lanciare il client di gioco, utilizzare il seguente comando:

``` bash
./briscola-online/gradlew client:run
```
