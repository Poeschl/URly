CREATE SEQUENCE links_id_seq;
CREATE TABLE links
(
    id            bigint  DEFAULT nextval('links_id_seq'::regclass) NOT NULL,
    original_url  varchar                                           NOT NULL,
    redirect_path varchar DEFAULT ''                                NOT NULL,
    type          varchar DEFAULT 'SHORT'                           NOT NULL,
    tracking      boolean DEFAULT FALSE                             NOT NULL,
    defending     boolean DEFAULT FALSE                             NOT NULL,
    created_at    timestamp                                         NOT NULL,
    PRIMARY KEY (id)
);
