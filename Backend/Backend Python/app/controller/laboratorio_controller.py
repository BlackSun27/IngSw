from dao.LaboratorioDAO import LaboratorioDAO
from typing import List
from database import get_db

class LaboratorioController:
    def __init__(self, db):
        self.dao = LaboratorioDAO(db)

    def aggiungi_laboratorio(self, nome: str, resp_sci: str, topic: str):
        self.dao.inserisci_laboratorio(nome, resp_sci, topic)

    def rimuovi_laboratorio(self, nome: str):
        self.dao.rimuovi_laboratorio(nome)

    def aggiungi_afferente(self, nome_lab: str, cf: str):
        self.dao.aggiungi_afferente(nome_lab, cf)

    def carica_afferenze(self, nome_lab: str) -> List[str]:
        l_cf = []
        self.dao.afferenze_lab(nome_lab, l_cf)
        return l_cf

    def carica_resp_sci(self, nome_lab: str) -> List[str]:
        resp = []
        self.dao.get_resp_sci(nome_lab, resp)
        return resp

    def carica_prog_lavora(self, nome_lab: str) -> List[str]:
        cup = []
        self.dao.get_prog_lavora(nome_lab, cup)
        return cup
