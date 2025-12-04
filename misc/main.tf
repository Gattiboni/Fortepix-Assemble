terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 5.0"
    }
  }
  required_version = ">= 1.4.0"
}

provider "google" {
  project = var.project_id
  region  = var.region
}

resource "google_compute_network" "fortepix_vpc" {
  name = "fortepix-vpc"
}

# Recursos adicionais (GKE, Cloud SQL, etc.) devem ser adicionados pela equipe técnica.
