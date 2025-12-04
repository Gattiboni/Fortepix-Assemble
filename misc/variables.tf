variable "project_id" {
  description = "ID do projeto GCP"
  type        = string
}

variable "region" {
  description = "Região GCP (ex.: us-central1)"
  type        = string
  default     = "us-central1"
}
