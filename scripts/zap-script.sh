#!/usr/bin/env bash

docker pull owasp/zap2docker-stable
docker run -i owasp/zap2docker-stable zap-baseline.py -t "https://d73ehpcuydjzg.cloudfront.net" -l PASS > zap_baseline_report.html

echo $? > /dev/null
