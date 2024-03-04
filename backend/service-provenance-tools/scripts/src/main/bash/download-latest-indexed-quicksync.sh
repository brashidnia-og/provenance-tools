#!/bin/bash

OBJECT_TO_DOWNLOAD=${OBJECT_TO_DOWNLOAD:-gs://provenance-testnet-backups/latest-v1_18_0_rc2-data-indexed.tar.gz}
OUTPUT_PATH="${OUTPUT_PATH:-/home/bobak/Downloads}"  # FOO will be assigned 'default' value if VARIABLE not set or null.

gcloud storage cp "${OBJECT_TO_DOWNLOAD}" "${OUTPUT_PATH}"
