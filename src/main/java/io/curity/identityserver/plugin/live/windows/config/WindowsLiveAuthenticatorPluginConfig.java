/*
 *  Copyright 2017 Curity AB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.curity.identityserver.plugin.live.windows.config;

import se.curity.identityserver.sdk.config.Configuration;
import se.curity.identityserver.sdk.config.annotation.DefaultString;
import se.curity.identityserver.sdk.config.annotation.DefaultURI;
import se.curity.identityserver.sdk.config.annotation.Description;
import se.curity.identityserver.sdk.service.SessionManager;

import java.net.URI;

@SuppressWarnings("InterfaceNeverImplemented")
public interface WindowsLiveAuthenticatorPluginConfig extends Configuration {
    @Description("client id")
    String getClientId();

    @Description("Secret key used for communication with windows-live")
    String getClientSecret();

    @Description("URL to the WindowsLive authorization endpoint")
    @DefaultURI("https://api.windows-live.com/oauth/authorize")
    URI getAuthorizationEndpoint();

    @Description("URL to the WindowsLive token endpoint")
    @DefaultURI("https://api.windows-live.com/oauth/access_token")
    URI getTokenEndpoint();

    @Description("A space-separated list of scopes to request from WindowsLive")
    @DefaultString("basic")
    String getScope();

    SessionManager getSessionManager();

}
