export const createOrEdit = (form, create, edit) => {
  console.log(form)
  return form.id == null ? create() : edit()
}