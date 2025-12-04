# FortePix - Mapeamento de códigos da API para telas/textos Flutter + camada JWT

Este pacote contém APENAS a camada adicional para:
- Mapear os códigos de erro FortePix no app Flutter
- Adicionar autenticação JWT + perfis na API

Integração:
1. Copie o conteúdo de `src/main/java` para dentro do `src/main/java` do seu projeto FortePix existente.
2. Adicione as dependências de Spring Security + JWT no seu `pom.xml` conforme o README abaixo.
3. Ajuste o Flutter para ler o campo `code` das respostas de erro e usar este mapeamento.
