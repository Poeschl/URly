CREATE SEQUENCE links_id_seq;
CREATE TABLE links
(
    id           bigint  DEFAULT nextval('links_id_seq'::regclass) NOT NULL,
    original_url varchar                                           NOT NULL,
    tracking     boolean DEFAULT FALSE                             NOT NULL,
    lengthening  boolean DEFAULT FALSE                             NOT NULL,
    defending    boolean DEFAULT FALSE                             NOT NULL,

    PRIMARY KEY (id)
);
