variable "vpc_cidr_block" {
  default = "10.0.0.0/16"
}

variable "subnet_cidr_block" {
  default = "10.0.10.0/24"
}

variable "avail_zone" {
  default = "ap-northeast-1a"
}

variable "env_prefix" {
  default = "dev"
}

variable "my_ip" {
  default = "126.75.103.133/32"
}

variable "ec2_server_ip" {
  default = "172.17.0.2/32"
}

variable "instance_type" {
  default = "t2.micro"
}
