terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws",
      version = "~>4.0"
    }
  }

  backend "s3" {
    bucket = "java-tf-state-bucket"
    key    = "java/state.tfstate"
    region = "ap-northeast-1"
  }
}

provider "aws" {
  region = "ap-northeast-1"
}
