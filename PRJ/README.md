# progetto-finale-di-pcad-2019

#Partecipanti:

1. Andres coronado
2. Lorenzo Astengo


# ABSTRACT
-------------

Progetto che permette di vedere diversi aspetti riguardante la sincronizzazione
tra processi in java.

il server fornisce un servizio di conteggio parole (ricevute)per area geografica 
ed il monitoraggio delle sessioni attive, e degli utenti registrati



# FEATURES
-------------

- Il progetto usa una struttura dati concorrente di tipo concurrentHashMap per
condividere la risorsa rappresentata in questo caso dalle parole contate
per area.

- RMI e Gateway Android

- Abbiamo usato metodi callable, rannable e Future<>

- I client sono disponibili in 3 varianti Android Swng e FX

- Il server e` disponibile in due varianti

- uso di thredpool, executors, swing workers, async tasks e
  listener per programmazione "ad eventi"



