from app.dao.ImpiegatoDAO import ImpiegatoDAO
from database import get_db

class ImpiegatoController:
    def __init__(self, db):
        self.dao = ImpiegatoDAO(db)

    def aggiungi_impiegato(self, cf, nome, cognome, datanascita, merito, codicecon, dataassunzione, categoria, salario, eta):
        self.dao.inserisci_impiegato(
            cf, nome, cognome, datanascita, dataassunzione, codicecon, merito, salario, categoria, eta
            )

    def rimuovi_impiegato(self, cf):
        self.dao.rimuovi_impiegato(cf)

    def promuovi_impiegato(self, cf, merito):
        self.dao.promuovi_impiegato(cf, merito)

    def carica_afferenze(self, cf):
        laboratori = []
        self.dao.get_afferenze_imp(cf, laboratori)
        return laboratori
