from flask import Blueprint, jsonify, request
from controller.gestionale_controller import GestionaleController
from database import get_db

gestionale_bp = Blueprint("gestionale", __name__, url_prefix="/api/gestionale")
db = next(get_db())
controller = GestionaleController(db)

@gestionale_bp.route("impiegati", methods=["GET"])
def get_impiegati():
    impiegati = controller.get_impiegati_db()
    return jsonify([imp.to.dict] for imp in impiegati)

@gestionale_bp.route("/laboratori", methods=["GET"])
def get_laboratori():
    laboratori = controller.get_laboratori_db()
    return jsonify([lab.to.dict] for lab in laboratori)

@gestionale_bp.route("/promozioni", methods=["GET"])
def get_promozioni():
    promozioni = controller.get_promozioni_db()
    return jsonify([prom.to.dict] for prom in promozioni)

@gestionale_bp.route("/progetti", methods=["GET"])
def get_progetti():
    progetti = controller.get_progetti_db()
    return jsonify([prog.to.dict] for prog in progetti)