from flask import Blueprint, jsonify, request
from app.controller.gestionale_controller import GestionaleController
from database import get_db

gestionale_bp = Blueprint("gestionale", __name__, url_prefix="/api/gestionale")
with next(get_db()) as db:
    controller = GestionaleController(db)

@gestionale_bp.route("/impiegati", methods=["GET"])
def get_impiegati():
    impiegati = controller.get_impiegati_db()
    impiegati_dict = [imp.to_dict() for imp in impiegati]
    return jsonify(impiegati_dict)

@gestionale_bp.route("/laboratori", methods=["GET"])
def get_laboratori():
    laboratori = controller.get_laboratori_db()
    laboratori_dict = [lab.to_dict() for lab in laboratori]
    return jsonify(laboratori_dict)

@gestionale_bp.route("/promozioni", methods=["GET"])
def get_promozioni():
    promozioni = controller.get_promozioni_db()
    promozioni_dict = [prom.to_dict() for prom in promozioni]
    return jsonify(promozioni_dict)

@gestionale_bp.route("/progetti", methods=["GET"])
def get_progetti():
    progetti = controller.get_progetti_db()
    progetti_dict = [prog.to_dict() for prog in progetti]
    return jsonify(progetti_dict)