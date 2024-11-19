from sqlalchemy.orm import Session
from dao.ProgettoDAO import ProjectDAO
from typing import List

class ProjectController:
    def __init__(self, db_session: Session):
        self.db = db_session

    def inserisci_progetto(self, cup: str, ref_sci: str, resp: str, nome: str, budget: float):
        try:
            ProjectDAO.insert_project(self.db, cup, ref_sci, resp, nome, budget)
            print(f"Progetto {nome} inserito con successo.")
        except Exception as e:
            print(f"Errore durante l'inserimento del progetto: {e}")
            raise

    def rimuovi_progetto(self, cup: str):
        try:
            ProjectDAO.remove_project(self.db, cup)
            print(f"Progetto con CUP {cup} rimosso con successo.")
        except Exception as e:
            print(f"Errore durante la rimozione del progetto: {e}")
            raise

    def aggiungi_laboratorio(self, cup: str, nome_lab: str):
        try:
            ProjectDAO.add_lab_to_project(self.db, cup, nome_lab)
            print(f"Laboratorio {nome_lab} aggiunto al progetto con CUP {cup}.")
        except Exception as e:
            print(f"Errore durante l'aggiunta del laboratorio: {e}")
            raise

    def ottieni_impiegati_progetto(self, cup: str) -> List[dict]:
        try:
            employees = ProjectDAO.get_project_employees(self.db, cup)
            print(f"Dipendenti trovati per il progetto con CUP {cup}: {employees}")
            return employees
        except Exception as e:
            print(f"Errore durante il recupero dei dipendenti del progetto: {e}")
            raise

    def ottieni_laboratori_progetto(self, cup: str) -> List[str]:
        try:
            labs = ProjectDAO.get_project_labs(self.db, cup)
            print(f"Laboratori trovati per il progetto con CUP {cup}: {labs}")
            return labs
        except Exception as e:
            print(f"Errore durante il recupero dei laboratori del progetto: {e}")
            raise RuntimeError(f"Errore durante il recupero dei laboratori per il progetto con CUP {cup}: {e}")