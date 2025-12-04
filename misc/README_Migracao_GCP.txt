FortePix – Pacote de Infraestrutura para Migração GCP
=====================================================

Este pacote contém arquivos de exemplo para apoiar a migração da API FortePix
para o Google Cloud Platform (GCP). Nenhum arquivo substitui a necessidade de
revisão e adaptação pela equipe técnica FortePix.

Estrutura:

- README_Migracao_GCP.txt
- docker/
    - Dockerfile_java
    - Dockerfile_python
- k8s/
    - deployment-java.yaml
    - deployment-python.yaml
    - service-java.yaml
    - service-python.yaml
    - ingress.yaml
- terraform/
    - main.tf
    - variables.tf
    - outputs.tf
- scripts/
    - gcloud_init.sh
    - gcloud_build_and_deploy_cloudrun.sh
    - gke_deploy.sh
    - cloudsql_setup.sql

Todos os arquivos são modelos simplificados, sem credenciais reais e sem
vinculação automática a nenhum projeto específico.
