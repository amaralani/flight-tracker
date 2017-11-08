-- CREATE SEQUENCE public.application_user_id_seq NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.application_user
  ALTER COLUMN id SET DEFAULT nextval('public.hibernate_sequence');

-- CREATE SEQUENCE public.role_id_seq NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.role
  ALTER COLUMN id SET DEFAULT nextval('public.hibernate_sequence');

delete from application_user;
delete from user_role;
delete from role;

INSERT INTO role (label, name)
VALUES ('کاربری', 'ROLE_USER'),
  ('مدیریت', 'ROLE_ADMIN'),
  ('مدیریت هواشناسی', 'ROLE_WEATHER_STATION_MANAGER'),
  ('مدیریت کاربران', 'ROLE_USER_MANAGER'),
  ('مدیریت پدیده ها', 'ROLE_EVENTS_MANAGER'),
  ('مدیریت یگان ها', 'ROLE_UNITS_MANAGER'),
  ('ساخت بولتن ', 'ROLE_ADD_BULLETIN'),
  ('مشاهده بولتن ', 'ROLE_VIEW_BULLETIN'),
  ('ساخت DEFACTOR', 'ROLE_ADD_DEFACTOR'),
  ('مشاهده DEFACTOR', 'ROLE_VIEW_DEFACTOR'),
  ('مدیریت دسترسی', 'ROLE_ACCESS_MANAGER'),
  ('مشاهده تاریخچه ورود', 'ROLE_VIEW_LOGIN_HISTORY'),
  ('بارگذاری فایل', 'ROLE_UPLOAD_FILES'),
  ('مشاهده گزارشات', 'ROLE_REPORT'),
  ('مدیریت شهرستان ها', 'ROLE_PROVINCES_MANAGER');