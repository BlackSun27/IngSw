import handleAPIResponse from "C:/Users/admin/Desktop/Appunti/INGSW/INGSW2324_52/Frontend/src/Services/handleAPIresponse.jsx";

const API_URL = "http://localhost:4000/api/progetto";

export const getProgetti = async () => {
    try {
        const response = await fetch("http://localhost:4000/api/gestionale/progetti");
        return handleAPIResponse(response, "Errore nel recupero dei progetti");
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};

export const aggiungiProgetto = async (progetto) => {
    try {
        const response = await fetch(`${API_URL}/`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(progetto),
        });

        const addedProject = await handleAPIResponse(response, "Errore nell'aggiunta del progetto");
        return addedProject;
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};


export const rimuoviProgetto = async (cup) => {
    try {
        const response = await fetch(`${API_URL}/${cup}`, {
            method: "DELETE",
        });
        return handleAPIResponse(response, "Errore nella rimozione del progetto");
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};

export const caricaImpiegati = async (cup) => {
    try {
      const response = await fetch(`${API_URL}/${cup}/impiegati`);
      const data = await handleAPIResponse(response, "Errore nel recupero degli impiegati associati al progetto");
      return {
        referente: data.referente,
        responsabile: data.responsabile,
      };
    } catch (error) {
      console.error("Errore nella chiamata API:", error);
      throw error;
    }
};
  

export const caricaLaboratori = async (cup) => {
    try {
        const response = await fetch(`${API_URL}/${cup}`);
        const data = await handleAPIResponse(response, "Errore nel recupero dei laboratori associati al progetto");
        return Array.isArray(data.laboratori) ? data.laboratori : [];
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};