from app.dao.ImpiegatoDAO import ImpiegatoDAO

class ImpiegatoController:
    def __init__(self, db):
        if not db:
            raise ValueError("La connessione al database non può essere nulla!")
        self.dao = ImpiegatoDAO(db)

    def aggiungi_impiegato(self, cf, nome, cognome, datanascita, merito, codicecon, dataassunzione, categoria, salario):
        self.dao.inserisci_impiegato(
            cf, nome, cognome, datanascita, dataassunzione, codicecon, merito, salario, categoria
            )

    def rimuovi_impiegato(self, cf):
        self.dao.rimuovi_impiegato(cf)

    def promuovi_impiegato(self, cf, merito):
        self.dao.promuovi_impiegato(cf, merito)

    def carica_afferenze(self, cf):
        laboratori = []
        self.dao.get_afferenze_imp(cf, laboratori)
        return laboratori
    
    def get_promozioni(self, cf):
        promozioni= []
        date= []
        self.dao.get_promozioni_imp(cf, promozioni, date)
        return dict(zip(promozioni, date))
    
    def get_progetti(self, cf: str) -> str:
        return self.dao.get_progetti_lab(cf)
