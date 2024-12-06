import React, { useState, useEffect } from 'react';
import {
  getProgetti,
  aggiungiProgetto,
  rimuoviProgetto,
  caricaImpiegati,
  caricaLaboratori,
} from './ProgettoService';
import { useNavigate } from "react-router-dom";

const ProgettiList = () => {
  const navigate = useNavigate();
  const [progetti, setProgetti] = useState([]);
  const [selectedProg, setSelectedProg] = useState(null);
  const [impiegati, setImpiegati] = useState([]);
  const [laboratori, setLaboratori] = useState([]);
  const [showAddForm, setShowAddForm] = useState(false);
  const [newProgetto, setNewProgetto] = useState({
    cup: "",
    refscie: "",
    respscie: "",
    nome: "",
    budget: "",
  });

  useEffect(() => {
    getProgetti()
      .then(setProgetti)
      .catch((error) => {
        if (error.message.includes("404")) {
          navigate("/not-found", { state: { message: "Nessun progetto trovato." } });
        } else {
          console.error("Errore:", error.message);
        }
      });
  }, [navigate]);

  const handleSelectProg = async (cup) => {
    setSelectedProg(cup);
    try {
      const impiegatiData = await caricaImpiegati(cup);
      setImpiegati(impiegatiData);

      const laboratoriData = await caricaLaboratori(cup);
      setLaboratori(laboratoriData);
    } catch (error) {
      if (error.message.includes("404")) {
        navigate("/not-found", { state: { message: `Il progetto ${cup} non è stato trovato.` } });
      } else {
        console.error(error.message);
      }
    }
  };

  const handleRimuoviProgetto = async () => {
    if (!selectedProg) return alert("Seleziona un progetto per rimuoverlo!");
    try {
      await rimuoviProgetto(selectedProg);
      setSelectedProg(null);
      const updatedProgetti = await getProgetti();
      setProgetti(updatedProgetti);
      setImpiegati([]);
      setLaboratori([]);
    } catch (error) {
      if (error.message.includes("404")) {
        navigate("/not-found", { state: { message: `Il progetto ${selectedProg} non è stato trovato.` } });
      } else {
        alert(`Errore nella rimozione del progetto: ${error.message}`);
      }
    }
  };

  const handleAggiungiProgetto = async (e) => {
    e.preventDefault();
    try {
      await aggiungiProgetto(newProgetto);
      alert("Progetto aggiunto con successo!");
      setNewProgetto({
        cup: "",
        refscie: "",
        respscie: "",
        nome: "",
        budget: "",
      });
      setShowAddForm(false);
      const updatedProgetti = await getProgetti();
      setProgetti(updatedProgetti);
    } catch (error) {
      if (error.message.includes("404")) {
        navigate("/not-found", { state: { message: "Impossibile aggiungere il progetto. Verifica i dati." } });
      } else {
        alert(`Errore nell'aggiunta del progetto: ${error.message}`);
      }
    }
  };

  return (
    <div className="scrollable-table">
      <div>
        <h1>Lista Progetti</h1>
        <table border="1">
          <thead>
            <tr>
              <th>CUP</th>
              <th>Referente Scientifico</th>
              <th>Responsabile</th>
              <th>Nome</th>
              <th>Budget</th>
            </tr>
          </thead>
          <tbody>
            {progetti.map((prog) => (
              <tr key={prog.cup}>
                <td>{prog.cup}</td>
                <td>{prog.refscie}</td>
                <td>{prog.respscie}</td>
                <td>{prog.nome}</td>
                <td>{prog.budget}</td>
                <td>
                  <button onClick={() => handleSelectProg(prog.cup)}>Mostra Dettagli</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {showAddForm && (
        <form onSubmit={handleAggiungiProgetto} style={{ marginTop: "20px" }}>
          <h2>Aggiungi Progetto</h2>
          <input
            name="cup"
            placeholder="Cup Progetto"
            value={newProgetto.cup}
            onChange={(e) => setNewProgetto({ ...newProgetto, cup: e.target.value })}
            required
          />
          <input
            name="referente_sci"
            placeholder="Referente Scientifico"
            value={newProgetto.refscie}
            onChange={(e) => setNewProgetto({ ...newProgetto, refscie: e.target.value })}
            required
          />
          <input
            name="responsabile"
            placeholder="Responsabile"
            value={newProgetto.respscie}
            onChange={(e) => setNewProgetto({ ...newProgetto, respscie: e.target.value })}
            required
          />
          <input
            name="nome"
            placeholder="Nome Progetto"
            value={newProgetto.nome}
            onChange={(e) => setNewProgetto({ ...newProgetto, nome: e.target.value })}
            required
          />
          <input
            name="budget"
            type="number"
            placeholder="Budget"
            value={newProgetto.budget}
            onChange={(e) => setNewProgetto({ ...newProgetto, budget: Number(e.target.value) })}
            required
          />
          <button type="submit">Aggiungi Progetto</button>
        </form>
      )}

      {selectedProg && (
        <div className="scrollable-table">
          <h2>Dettagli per {selectedProg}</h2>
          <h3>Impiegati</h3>
          <ul>
            {impiegati.map((cf, index) => (
              <li key={index}>{cf}</li>
            ))}
          </ul>
          <h3>Laboratori</h3>
          <ul>
            {laboratori.map((nome, index) => (
              <li key={index}>{nome}</li>
            ))}
          </ul>
        </div>
      )}

      <div>
        <button onClick={handleRimuoviProgetto}>Rimuovi Progetto</button>
        <button onClick={() => setShowAddForm((prev) => !prev)}>
          {showAddForm ? "Annulla Aggiunta" : "Aggiungi Progetto"}
        </button>
      </div>
    </div>
  );
};

export default ProgettiList;