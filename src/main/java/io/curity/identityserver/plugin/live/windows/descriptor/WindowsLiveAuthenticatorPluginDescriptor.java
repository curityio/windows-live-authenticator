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

package io.curity.identityserver.plugin.live.windows.descriptor;

import io.curity.identityserver.plugin.live.windows.authentication.CallbackRequestHandler;
import io.curity.identityserver.plugin.live.windows.authentication.WindowsLiveAuthenticatorRequestHandler;
import io.curity.identityserver.plugin.live.windows.config.WindowsLiveAuthenticatorPluginConfig;
import se.curity.identityserver.sdk.authentication.AuthenticatorRequestHandler;
import se.curity.identityserver.sdk.plugin.descriptor.AuthenticatorPluginDescriptor;

import java.util.LinkedHashMap;
import java.util.Map;

public final class WindowsLiveAuthenticatorPluginDescriptor
        implements AuthenticatorPluginDescriptor<WindowsLiveAuthenticatorPluginConfig>
{
    public final static String INDEX = "index";
    public final static String CALLBACK = "callback";

    @Override
    public String getPluginImplementationType()
    {
        return "windows-live";
    }

    @Override
    public Class<? extends WindowsLiveAuthenticatorPluginConfig> getConfigurationType()
    {
        return WindowsLiveAuthenticatorPluginConfig.class;
    }

    @Override
    public Map<String, Class<? extends AuthenticatorRequestHandler<?>>> getAuthenticationRequestHandlerTypes()
    {
        Map<String, Class<? extends AuthenticatorRequestHandler<?>>> handlers = new LinkedHashMap<>(2);

        handlers.put("index", WindowsLiveAuthenticatorRequestHandler.class);
        handlers.put(CALLBACK, CallbackRequestHandler.class);

        return handlers;
    }
}
