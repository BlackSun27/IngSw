from sqlalchemy.orm import Session
from sqlalchemy.sql import text
from typing import List
from datetime import date

class ImpiegatoDAO:
    def __init__(self, db: Session):
        self.db = db

    def inserisci_impiegato(
        self, cf: str, nome: str, cognome: str, datanascita: date, dataassunzione: date, 
        codicecon: str, merito: bool, salario: float, categoria: str
    ):
        query = text("""
            CALL inserisciimpiegato(
                :cf, :nome, :cognome, :datanascita, :merito, 
                :codicecon, :dataassunzione, :categoria, :salario
            )
        """)
        params = {
            "cf": cf,
            "nome": nome,
            "cognome": cognome,
            "datanascita": datanascita,
            "merito": merito,
            "codicecon": codicecon,
            "dataassunzione": dataassunzione,
            "categoria": categoria,
            "salario": salario
        }
        try:
            self.db.execute(query, params)
            self.db.commit()
        except Exception as e:
            self.db.rollback()
            raise RuntimeError(f"Errore nell'inserimento dell'impiegato: {e}")

    def rimuovi_impiegato(self, cf: str):
        query = text("CALL rimuovi_impiegato(:cf)")
        try:
            self.db.execute(query, {"cf": cf})
            self.db.commit()
        except Exception as e:
            self.db.rollback()
            raise RuntimeError(f"Errore nella rimozione dell'impiegato: {e}")

    def promuovi_impiegato(self, cf: str, merito: bool):
        query = text("CALL inseriscipromozioni(:cf, :merito)")
        try:
            self.db.execute(query, {"cf": cf, "merito": merito})
            self.db.commit()
        except Exception as e:
            self.db.rollback()
            raise RuntimeError(f"Errore nella promozione dell'impiegato: {e}")

    def get_afferenze_imp(self, cf: str, laboratorio: List[str]):
        query = text("SELECT nomelab FROM utilizza WHERE cf = :cf")
        try:
            result = self.db.execute(query, {"cf": cf})
            for row in result:
                laboratorio.append(row["nomelab"])
        except Exception as e:
            raise RuntimeError(f"Errore nel recupero delle afferenze: {e}")

    def get_progetti_lab(self, cf: str, progetto: List[str]):
        query = text("SELECT * FROM lavora_view(:cf)")
        try:
            result = self.db.execute(query, {"cf": cf})
            for row in result:
                progetto.append(row[0])  # Assume il nome progetto sia la prima colonna
        except Exception as e:
            raise RuntimeError(f"Errore nel recupero dei progetti: {e}")

    def get_promozioni_imp(self, cf: str, l_promozioni: List[str], date: List[date]):
        query = text("SELECT * FROM promozioni WHERE cf = :cf")
        try:
            result = self.db.execute(query, {"cf": cf})
            for row in result:
                l_promozioni.append(row["nuovacategoria"])
                date.append(row["datapassaggio"])
        except Exception as e:
            raise RuntimeError(f"Errore nel recupero delle promozioni: {e}")
