from decimal import Decimal
from typing import Dict


class CruzamentoDadosService:
    def __init__(self,
                 historico_scores: Dict[str, int] | None = None,
                 historico_tentativas_fraude: Dict[str, int] | None = None):
        self.historico_scores = historico_scores or {}
        self.historico_tentativas_fraude = historico_tentativas_fraude or {}

    def calcular_score_usuario(self, documento: str, chave_destino: str, valor: Decimal | None) -> int:
        base = self.historico_scores.get(documento, 50)
        tentativas = self.historico_tentativas_fraude.get(documento, 0)

        ajuste_fraude = -tentativas * 10
        ajuste_valor = -10 if (valor is not None and valor > Decimal("5000")) else 0

        score_final = base + ajuste_fraude + ajuste_valor
        score_final = max(0, min(100, score_final))
        return score_final
