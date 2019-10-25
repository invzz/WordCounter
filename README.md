# Word Counter

#Partecipanti:

1. Andres coronado
2. Lorenzo Astengo


# ABSTRACT
-------------

Progetto che permette di vedere diversi aspetti riguardante la sincronizzazione
tra processi in java, interfaccie grafiche. 

il server fornisce un servizio di conteggio parole (ricevute)per area geografica 
ed il monitoraggio delle sessioni attive, degli utenti registrati attraverso una
struttura dati concorrente e visivamente attraverso Jtree



# FEATURES
-------------

- Il progetto usa una struttura dati concorrente di tipo concurrentHashMap per
condividere la risorsa rappresentata in questo caso dalle parole contate
per area.

- RMI, e Gateway Android

- Abbiamo usato interfacce Serializable, callable, runnable e tipi Future<>

- I client sono disponibili in 3 varianti Android Swing e openJavaFX

- Il server e` disponibile in due varianti

- Uso di thredpool, executors, swing workers, async tasks e
  listener per programmazione "ad eventi"

- Swing Jtree, DataModels ecc...

- Concurrent HashMap

- Alcune funzioni lambda

- tipi inferiti <?>

  




