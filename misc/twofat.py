import random
import time
from dataclasses import dataclass
from typing import Dict, Optional


@dataclass
class RegistroCodigo:
    codigo: str
    criado_em: float


class TwoFATService:
    def __init__(self, validade_segundos: int = 180):
        self.validade_segundos = validade_segundos
        self.codigos: Dict[str, RegistroCodigo] = {}

    def gerar_codigo(self, identificador_usuario: str) -> str:
        codigo = f"{random.randint(0, 999999):06d}"
        self.codigos[identificador_usuario] = RegistroCodigo(codigo=codigo, criado_em=time.time())
        return codigo

    def validar_codigo(self, identificador_usuario: str, codigo_informado: str) -> bool:
        registro: Optional[RegistroCodigo] = self.codigos.get(identificador_usuario)
        if registro is None:
            return False
        if registro.codigo != codigo_informado:
            return False
        if time.time() > registro.criado_em + self.validade_segundos:
            self.codigos.pop(identificador_usuario, None)
            return False
        self.codigos.pop(identificador_usuario, None)
        return True
