# TW Geeknight Demo

## **PreRequisite**:

1. Install Java
> Note: Java version requirement depends on GOCD version being used. 
>For DEMO, we have used GOCD Version 20.3.0, hence Java 11 is needed.
2. Install Docker
3. Install GoServer
> Installation instruction as per OS are available on [GOCD official site](https://docs.gocd.org/current/installation/installing_go_server.html)
4. Install GoAgent
> Installation instruction as per OS are available on [GOCD official site](https://docs.gocd.org/current/installation/installing_go_agent.html)
5. Registering your agent with the server
Open the GoCD server dashboard
> If localhost, server url is: http://localhost:8153/go
Follow the instructions [here](https://docs.gocd.org/current/configuration/managing_a_build_cloud.html) to find the agent you’ve just installed on the list and add the agent to your cloud. The GoCD server will now schedule work for this agent.
6. Docker Hub Account
7. Configure Docker Registry Artifact Plugin
> Steps to install and configure plugin are mentioned [here](https://github.com/gocd/docker-registry-artifact-plugin). This plugin allows us to push/pull artifacts to/from docker hub

## **Steps to build CICD pipeline for your project**
1. Commit your code in Github code repository.
2. Register your repo in GOCD.
> Go to Admin tab, open "Config XML".
> Add below lines in file:
```
  <config-repos>
    <config-repo id="gocd_demo" pluginId="yaml.config.plugin">
      <git url="https://github.com/ThoughtWorksInc/gocd_demo.git" />
      <rules>
        <allow action="refer" type="pipeline_group">demo_app</allow>
        <deny action="refer" type="pipeline_group">*</deny>
      </rules>
    </config-repo>
  </config-repos>
```

## **Settings to be modified**
Change below environment variables in pipeline.gocd.yaml file:
```
DOCKERHUB_USERNAME: <replace with your docker hub username>

id: <replace with your artifact_store_id configured in Step 7 of Prerequisite>
store_id: <replace with your artifact_store_id configured in Step 7 of Prerequisite>
artifact_id: <replace with your artifact_store_id configured in Step 7 of Prerequisite>
```