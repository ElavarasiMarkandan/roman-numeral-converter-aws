exec java \
  -javaagent:/app/apm-agent.jar \
  -Delastic.apm.service_name=roman-numeral-converter \
  -Delastic.apm.server_url=http://apm-server:8200 \
  -Delastic.apm.application_packages=com.romannumeral \
  -jar app.jar