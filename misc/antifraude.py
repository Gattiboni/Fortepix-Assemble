from decimal import Decimal
from typing import Set


class ResultadoRisco:
    APROVADO = "APROVADO"
    BLOQUEIO_PREVENTIVO = "BLOQUEIO_PREVENTIVO"
    ALTO_RISCO = "ALTO_RISCO"


class AntifraudeService:
    def __init__(self, chaves_alto_risco: Set[str],
                 limite_bloqueio_preventivo: Decimal,
                 limite_alto_risco: Decimal):
        self.chaves_alto_risco = chaves_alto_risco
        self.limite_bloqueio_preventivo = limite_bloqueio_preventivo
        self.limite_alto_risco = limite_alto_risco

    def avaliar(self, chave_destino: str, valor: Decimal | None, score_usuario: int) -> str:
        if chave_destino in self.chaves_alto_risco:
            return ResultadoRisco.ALTO_RISCO
        if valor is not None and valor > self.limite_alto_risco:
            return ResultadoRisco.ALTO_RISCO
        if valor is not None and valor > self.limite_bloqueio_preventivo:
            return ResultadoRisco.BLOQUEIO_PREVENTIVO
        if score_usuario < 40:
            return ResultadoRisco.BLOQUEIO_PREVENTIVO
        return ResultadoRisco.APROVADO
