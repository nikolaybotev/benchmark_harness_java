#!/bin/bash

for b in DeltaBlue Richards tracer.Tracer; do
  echo -n $b,
  java -cp bin/ example.${b}Example > /dev/null

  shortname=${b##*.}

  for i in {1..3}; do
    java -cp bin/ example.${b}Example | awk "/^$shortname/ { ORS=\"\"; print \$2 \",\" }"
  done
  echo
done

