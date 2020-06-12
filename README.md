# TW Geeknight Demo

## **PreRequisite**:

1. Install Java
> Note: Java version requirement depends on GOCD version being used.
> For DEMO, we have used GOCD Version 20.3.0, hence Java 11 is needed.
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

id: <replace with your artifact_store_id>
store_id: <replace with your artifact_store_id>
artifact_id: <replace with your artifact_store_id>
```

## Webhook settings
GoCD  polls to your version control (in this case, Github) in every 1 minutes. So, it might be the case that you have to wait for some time period after commit your changes to automatic trigger the pipeline.<br>
A nice workaround to this problem is using [Github Webhooks](https://developer.github.com/webhooks/). In this case Github will put a HTTP POST payload to the webhook's configured URL for every push event (configurable). But you need a dedicated URL for this. As we are running GoCD in our localhost, webhooks will not accept local server connection. For this we are using `ngrok` application which provide you a public address bind to your localhost and port 8153 (configurable port, as 8153 is port used by GoCD-Server). You can download it from [here](https://ngrok.com/download).
After downloading and extracting the executable file, follow these steps - <br>
1. use this command `./ngrok http 8153` and it will give you a http and https url like this <br>

```bash
ngrok by @inconshreveable                                                                                                                                                                       (Ctrl+C to quit)

Session Status                online
Session Expires               7 hours, 59 minutes
Version                       2.3.35
Region                        United States (us)
Web Interface                 http://127.0.0.1:4040
Forwarding                    http://222fa0417d34.ngrok.io -> http://localhost:8153
Forwarding                    https://222fa0417d34.ngrok.io -> http://localhost:8153

Connections                   ttl     opn     rt1     rt5     p50     p90
                              0       0       0.00    0.00    0.00    0.00
```

2. Your public URL is `https://222fa0417d34.ngrok.io` (Valid for 8 hours). Now go to webhooks setting of your github repository and add this public URL along with the provided endpoint `https://222fa0417d34.ngrok.io/go/api/webhooks/github/notify`. Now webhook will notify every push event to `go/api/webhooks/github/notify` endpoint of GoCD server.
3. Set the `Content type` to `application/json`
4. Provide the secret. You will get it from your cruise-config.xml file in gocd-server folder. Here is the path `${PATH_TO_GOCD_SERVER_FOLDER}/config/cruise-config.xml` . Here look for `webhookSecret` and paste the value to your secret field on webhook settings. Now save all the setting you have done <br>
Now you webhook is ready. Look for the `green tick` in webhooks list. If you got this, Congrats, your webhook is connected otherwise you have done something wrong, check again the settings.