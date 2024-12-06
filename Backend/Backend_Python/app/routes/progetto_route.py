from flask import Blueprint, request, jsonify
from app.controller.progetto_controller import ProjectController
from database import get_db

progetto_bp = Blueprint("progetto", __name__, url_prefix="/api/progetto")

@progetto_bp.route("/", methods=["POST"])
def inserisci_progetto():
    data = request.json
    with next(get_db()) as db:
        print(f"Sessione del nuovo database {db}")
        controller = ProjectController(db)
    try:
        controller.inserisci_progetto(
            data["cup"], data["ref_sci"], data["resp"],
            data["nome"], data["budget"]
        )
        return jsonify({"message":"Progetto {cup} inserito con successo!"}), 201
    except Exception as e:
        return jsonify({"error": str(e)})
    
@progetto_bp.route("/<cup>", methods = ["DELETE"])
def rimuovi_progetto(cup):
    with next(get_db()) as db:
        controller = ProjectController(db)
    try:
        controller.rimuovi_progetto(cup)
        return jsonify({"message": "Progetto {cup} rimosso con successo!"}), 200
    except Exception as e:
        return jsonify({"error": str(e)})

@progetto_bp.route("/<cup>/laboratori", methods=["POST"])
def aggiungi_laboratorio_progetto(cup):
    with next(get_db()) as db:
        controller = ProjectController(db)
    data = request.json
    try:
        controller.aggiungi_laboratorio(cup, data["nome_lab"])
        return jsonify({"message": "Laboratorio aggiunto al progetto {cup} con successo!"}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400

@progetto_bp.route("/<cup>/impiegati", methods=["GET"])
def ottieni_impiegati_progetto(cup):
    with next(get_db()) as db:
        controller = ProjectController(db)
    try:
        impiegati = controller.ottieni_impiegati_progetto(cup)
        return jsonify({"impiegati": impiegati})
    except Exception as e:
        return jsonify({"error": str(e)}), 400

    
@progetto_bp.route("/<cup>", methods=["GET"])
def ottieni_laboratori_progetto(cup):
    with next(get_db()) as db:
        controller = ProjectController(db)
    try:
        laboratori = controller.ottieni_laboratori_progetto(cup)
        return jsonify({"laboratori": laboratori})
    except Exception as e:
        return jsonify({"error":str(e)}),400