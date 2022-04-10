#!/bin/bash

gcloud compute instances create pb-instance \
  --image-family=ubuntu-2004-focal-v20220204 \
  --machine-type=e2-micro \
  --zone=us-west1-b \
  --boot-disk-type=ssd \
  --can-ip-forward

gcloud compute instances create pb-instance \
  --image=provenance-ubuntu-image-v1 \
  --image-project=provenance-localnet \
  --machine-type=e2-micro \
  --zone=us-west1-b \
  --boot-disk-type=ssd \
  --can-ip-forward