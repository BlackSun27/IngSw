import React, { useState, useEffect } from "react";
import { getPromozioni, caricaAfferenze, getImpiegati } from "./ImpiegatoService";

const ImpiegatiList = () => {
  const [impiegati, setImpiegati] = useState([]);
  const [selectedCF, setSelectedCF] = useState(null);
  const [promozioni, setPromozioni] = useState([]);
  const [afferenze, setAfferenze] = useState([]);
  const [progetto, setProgetto] = useState([]);

  useEffect(() => {
    getImpiegati()
      .then(setImpiegati)
      .catch((err) => console.error("Errore:", err));
  }, []);

  const handleSelectCF = async (cf) => {
    setSelectedCF(cf);

    try {
      const promozioniData = await getPromozioni(cf);
      setPromozioni(promozioniData);

      const afferenzeData = await caricaAfferenze(cf);
      setAfferenze(afferenzeData);

      const progettoData = await caricaProgetti(cf);
      setProgetto(progettoData);

    } catch (error) {
      console.error(error.message);
    }
  };

  return (
    <div style={{ display: "flex" }}>
      <div>
        <h1>Lista Impiegati</h1>
        <table border="1">
          <thead>
            <tr>
              <th>CF</th>
              <th>Nome</th>
              <th>Cognome</th>
              <th>Categoria</th>
              <th>Data Assunzione</th>
              <th>Data di Nascita</th>
              <th>Età</th>
              <th>Salario</th>
              <th>Merito</th>
            </tr>
          </thead>
          <tbody>
            {impiegati.map((impiegato) => (
              <tr key={impiegato.cf}>
                <td>{impiegato.cf}</td>
                <td>{impiegato.nome}</td>
                <td>{impiegato.cognome}</td>
                <td>{impiegato.categoria}</td>
                <td>{impiegato.dataassunzione}</td>
                <td>{impiegato.datanascita}</td>
                <td>{impiegato.eta}</td>
                <td>{impiegato.salario}</td>
                <td>{impiegato.merito ? "Sì" : "No"}</td>
                <td>
                  <button onClick={() => handleSelectCF(impiegato.cf)}>
                    Mostra Dettagli
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {selectedCF && (
        <div style={{ marginLeft: "20px" }}>
          <h2>Dettagli per {selectedCF}</h2>

          <h3>Promozioni</h3>
          <table border="1">
            <thead>
              <tr>
                <th>Categoria</th>
                <th>Data Passaggio</th>
              </tr>
            </thead>
            <tbody>
              {promozioni.map((promo, index) => (
                <tr key={index}>
                  <td>{promo.categoria}</td>
                  <td>{promo.datapassaggio}</td>
                </tr>
              ))}
            </tbody>
          </table>

          <h3>Progetti e Afferenze</h3>
          <table border="1">
            <thead>
              <tr>
                <th>Nome Laboratorio</th>
                <th>CUP Progetto</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{afferenze.nomelab}</td>
                <td>{progetto.cup}</td>
              </tr>
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default ImpiegatiList;