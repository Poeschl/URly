# URly

This software is a self-hosted url-shortener with a view quirks.
The registered link can be customized to be tracked, defended and maybe more.
See the features below.

![The list of registered links](./docs/list.png)

## Features

* Shortens any Link to a short-code with at least 6 characters or to a long one (length of 1024 chars).
* Allows tracking of the link click via Plausible
* Defender-Mode - Forwards the user to a few "Checking link" pages. "Like a defender does"
* And even more annoying Defender mode. This time with removing anchors and adding some query parameters.
* Special treatment when the referer is the "real" Defender.

## Defender-Mode for Links

![The landing page for a defended link](./docs/defender.png)

The Defender mode is a pun against the Microsoft Cloud Defender which currently causes me some headache at work.
So I decided to make a similar user experience which can be opt-in per Link.

If enabled the user will be routed to a redirection page similar to the image above.
After a few seconds a redirection to the real target takes place.
There is no actual checking something. At least for now.

## Setup

The preferred setup is via the provided docker-compose (`deploy` folder) file.
It will provide all necessary environment variables and the right configuration of URly.

For the backend there are some additional environment variables which needs to be set:

* `URLY_HASH_SECRET` - This can be any string and will affect the tokens we will generate.
  If not set, your short-codes can be reused on any other URly instance.
* `URLY_PLAUSIBLE_ENABLED` - Enables the tracking via Plausible.
  If this is not enabled the tracking of links will not work.
* `URLY_PLAUSIBLE_DOMAIN` - The domain which was inserted into Plausible.
* `URLY_PLAUSIBLE_API_HOST` - An custom Plausible api host, if you are not using `https://plausible.io`.
  (This should always include the protocol, like `https://mytrack.example`)

__Note: This setup has no authentication build-in! You need to take care of this by yourself!__
The paths to exclude from the authentication to make the links public available are:

* `/s/` (Short codes)
* `/e/` (Errors)
* `/d/` (Defender)
* `/img/` (Defender)
* `/assets/` (Defender)

## Note

This software will get no versioning and lives on the bloody main branch.

## Development

For development there is a little script in the project root named `start_dev_env.sh`.
Executing it as well as `.gradlew backend:bootRun` (from project root) and `npm run dev` (from the `frontend` folder)
will set up the local environment on http://localhost:8888.
