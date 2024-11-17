from sqlalchemy.orm import Session
from sqlalchemy.sql import text
from typing import List
from database import get_db


class LaboratorioDAO:
    def __init__(self, db: Session):
        self.db = db

    def inserisci_laboratorio(self, nome: str, resp_sci: str, topic: str):
        query = text("CALL inseriscilaboratorio(:nome, :resp_sci, :topic)")
        params = {"nome": nome, "resp_sci": resp_sci, "topic": topic}
        try:
            self.db.execute(query, params)
            self.db.commit()
        except Exception as e:
            self.db.rollback()
            raise RuntimeError(f"Errore nell'inserimento del laboratorio: {e}")

    def rimuovi_laboratorio(self, nome: str):
        query = text("CALL rimuovi_laboratorio(:nome)")
        try:
            self.db.execute(query, {"nome": nome})
            self.db.commit()
        except Exception as e:
            self.db.rollback()
            raise RuntimeError(f"Errore nella rimozione del laboratorio: {e}")

    def aggiungi_afferente(self, nome_lab: str, cf: str):
        query = text("CALL inserisciutilizza(:nome_lab, :cf)")
        params = {"nome_lab": nome_lab, "cf": cf}
        try:
            self.db.execute(query, params)
            self.db.commit()
        except Exception as e:
            self.db.rollback()
            raise RuntimeError(f"Errore nell'aggiunta di un afferente: {e}")

    def afferenze_lab(self, nome_lab: str, l_cf: List[str]):
        query = text("SELECT cf FROM utilizza WHERE nomelab = :nome_lab")
        try:
            result = self.db.execute(query, {"nome_lab": nome_lab})
            for row in result:
                l_cf.append(row["cf"])
        except Exception as e:
            raise RuntimeError(f"Errore nel recupero delle afferenze: {e}")

    def get_resp_sci(self, nome_lab: str, resp: List[str]):
        query = text("""
            SELECT i.cf, i.nome, i.cognome
            FROM laboratorio AS l
            JOIN impiegato AS i ON l.respscie = i.cf
            WHERE l.nome = :nome_lab
        """)
        try:
            result = self.db.execute(query, {"nome_lab": nome_lab})
            for row in result:
                resp.append(row["cf"])
                resp.append(row["nome"])
                resp.append(row["cognome"])
        except Exception as e:
            raise RuntimeError(f"Errore nel recupero del responsabile scientifico: {e}")

    def get_prog_lavora(self, nome_lab: str, cup: List[str]):
        query = text("""
            SELECT cup 
            FROM lavora 
            WHERE lab1 = :nome_lab OR lab2 = :nome_lab OR lab3 = :nome_lab
        """)
        try:
            result = self.db.execute(query, {"nome_lab": nome_lab})
            for row in result:
                cup.append(row["cup"])
        except Exception as e:
            raise RuntimeError(f"Errore nel recupero dei progetti collegati: {e}")
