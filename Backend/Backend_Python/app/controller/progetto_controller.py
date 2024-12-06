from app.dao.ProgettoDAO import ProjectDAO
from typing import List

class ProjectController:
    def __init__(self, db):
        if not db:
            raise ValueError("La connessione al database non può essere nulla!")
        self.dao = ProjectDAO(db)

    def inserisci_progetto(self, cup: str, ref_sci: str, resp: str, nome: str, budget: float):
        if not isinstance(budget, (int, float)):
            raise ValueError("Il budget deve essere un numero.")
        self.dao.insert_project(cup, ref_sci, resp, nome, budget)

    def rimuovi_progetto(self, cup: str):
        self.dao.remove_project(cup)

    def aggiungi_laboratorio(self, cup: str, nome_lab: str):
        self.dao.add_lab_to_project(cup, nome_lab)

    def ottieni_impiegati_progetto(self, cup: str) -> List[dict]:
        return self.dao.get_project_employees(cup)

    def ottieni_laboratori_progetto(self, cup: str) -> List[dict]:
        return self.dao.get_project_labs(cup)