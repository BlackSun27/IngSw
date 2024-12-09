import handleAPIResponse from "C:/Users/admin/Desktop/Appunti/INGSW/INGSW2324_52/Frontend/src/Services/handleAPIresponse.jsx";

const API_URL = "http://localhost:4000/api/laboratori";

export const getLaboratori = async () => {
    try {
        const response = await fetch('http://localhost:4000/api/gestionale/laboratori');
        return handleAPIResponse(response, "Errore nel prelievo dei laboratori");
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};

export const aggiungiLaboratori = async (laboratorio) => {
    try {
        const response = await fetch(`${API_URL}/`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(laboratorio),
        });
        return handleAPIResponse(response, "Errore nell'aggiunta del laboratorio");
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};

export const rimuoviLaboratorio = async (nome) => {
    try {
        const response = await fetch(`${API_URL}/${nome}`, {
            method: "DELETE",
        });
        return handleAPIResponse(response, "Errore nella rimozione del laboratorio");
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};

export const caricaAfferenze = async (nome) => {
    try {
        const response = await fetch(`${API_URL}/${nome}`);
        return handleAPIResponse(response, "Errore nel recupero degli impiegati che lavorano al laboratorio");
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};

export const caricaResponsabile = async (nome) => {
    try {
        const response = await fetch(`${API_URL}/${nome}/responsabile`);
        return handleAPIResponse(response, "Errore nel recupero del responsabile del laboratorio");
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};

export const caricaProgetti = async (nome) => {
    try {
        const response = await fetch(`${API_URL}/${nome}/progetti`);
        return handleAPIResponse(response, "Errore nel recupero del progetto del laboratorio");
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};