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

package io.curity.identityserver.plugin.live.windows.authentication;

import io.curity.identityserver.plugin.live.windows.config.WindowsLiveAuthenticatorPluginConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.curity.identityserver.sdk.attribute.Attribute;
import se.curity.identityserver.sdk.authentication.AuthenticationResult;
import se.curity.identityserver.sdk.authentication.AuthenticatorRequestHandler;
import se.curity.identityserver.sdk.errors.ErrorCode;
import se.curity.identityserver.sdk.http.RedirectStatusCode;
import se.curity.identityserver.sdk.service.ExceptionFactory;
import se.curity.identityserver.sdk.service.authentication.AuthenticatorInformationProvider;
import se.curity.identityserver.sdk.web.Request;
import se.curity.identityserver.sdk.web.Response;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static io.curity.identityserver.plugin.live.windows.descriptor.WindowsLiveAuthenticatorPluginDescriptor.CALLBACK;

public class WindowsLiveAuthenticatorRequestHandler implements AuthenticatorRequestHandler<Request>
{
    private static final Logger _logger = LoggerFactory.getLogger(WindowsLiveAuthenticatorRequestHandler.class);
    private static final String AUTHORIZATION_ENDPOINT = "https://login.live.com/oauth20_authorize.srf";

    private final WindowsLiveAuthenticatorPluginConfig _config;
    private final AuthenticatorInformationProvider _authenticatorInformationProvider;
    private final ExceptionFactory _exceptionFactory;

    public WindowsLiveAuthenticatorRequestHandler(WindowsLiveAuthenticatorPluginConfig config)
    {
        _config = config;
        _exceptionFactory = config.getExceptionFactory();
        _authenticatorInformationProvider = config.getAuthenticatorInformationProvider();
    }

    @Override
    public Optional<AuthenticationResult> get(Request request, Response response)
    {
        _logger.debug("GET request received for authentication");

        String redirectUri = createRedirectUri();
        String state = UUID.randomUUID().toString();
        Map<String, Collection<String>> queryStringArguments = new LinkedHashMap<>(5);
        Set<String> scopes = new LinkedHashSet<>(7);

        _config.getSessionManager().put(Attribute.of("state", state));

        queryStringArguments.put("client_id", Collections.singleton(_config.getClientId()));
        queryStringArguments.put("redirect_uri", Collections.singleton(redirectUri));
        queryStringArguments.put("state", Collections.singleton(state));
        queryStringArguments.put("response_type", Collections.singleton("code"));

        handleScopes(scopes);
        queryStringArguments.put("scope", Collections.singleton(String.join(" ", scopes)));

        _logger.debug("Redirecting to {} with query string arguments {}", AUTHORIZATION_ENDPOINT,
                queryStringArguments);

        throw _exceptionFactory.redirectException(AUTHORIZATION_ENDPOINT,
                RedirectStatusCode.MOVED_TEMPORARILY, queryStringArguments, false);
    }

    private void handleScopes(Set<String> scopes)
    {
        scopes.add("wl.basic");
        if (_config.isOfflineAccount())
        {
            scopes.add("wl.offline_access");
        }
        if (_config.isSingleSignin())
        {
            scopes.add("wl.signin");
        }
        if (_config.isBirthdayInfo())
        {
            scopes.add("wl.birthday");
        }
        switch (_config.getCalendarsInfo())
        {
            case WRITE:
                scopes.add("wl.calendars_update");
            case READ:
                scopes.add("wl.calendars");
        }
        if (_config.isContactsBirthday())
        {
            scopes.add("wl.contacts_birthday");
        }
        if (_config.isContactsCreate())
        {
            scopes.add("wl.contacts_create");
        }
        if (_config.isCalendarAndEvents())
        {
            scopes.add("wl.contacts_calendars");
        }
        if (_config.isContactsPhotos())
        {
            scopes.add("wl.contacts_photos");
        }
        if (_config.isContactsOneDrive())
        {
            scopes.add("wl.contacts_skydrive");
        }
        if (_config.isEmailsAccess())
        {
            scopes.add("wl.emails");
        }
        if (_config.isEventsCreate())
        {
            scopes.add("wl.events_create");
        }
        if (_config.isIMAP())
        {
            scopes.add("wl.imap");
        }
        if (_config.isPhoneNumbersAccess())
        {
            scopes.add("wl.phone_numbers");
        }
        if (_config.isPhotosAccess())
        {
            scopes.add("wl.photos");
        }
        if (_config.isPostalAddresses())
        {
            scopes.add("wl.postal_addresses");
        }
        switch (_config.getOneDriveAccess())
        {
            case WRITE:
                scopes.add("wl.skydrive_update");
            case READ:
                scopes.add("wl.skydrive");
        }
        if (_config.isWorkProfileInfo())
        {
            scopes.add("wl.work_profile");
        }
        if (_config.isOneNoteAccess())
        {
            scopes.add("office.onenote_create");
        }
    }

    private String createRedirectUri()
    {
        try
        {
            URI authUri = _authenticatorInformationProvider.getFullyQualifiedAuthenticationUri();

            return new URL(authUri.toURL(), authUri.getPath() + "/" + CALLBACK).toString();
        } catch (MalformedURLException e)
        {
            throw _exceptionFactory.internalServerException(ErrorCode.INVALID_REDIRECT_URI,
                    "Could not create redirect URI");
        }
    }

    @Override
    public Optional<AuthenticationResult> post(Request request, Response response)
    {
        throw _exceptionFactory.methodNotAllowed();
    }

    @Override
    public Request preProcess(Request request, Response response)
    {
        return request;
    }
}
