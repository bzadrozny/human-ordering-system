export const dateToString = (date) => {
  if (date == null) {
    return undefined
  }
  let date_ = date.toLocaleDateString().split('.')
  if (date_[0] < 9) {
    date_[0] = '0' + date_[0]
  }
  return date_[2] + '-' + date_[1] + '-' + date_[0]
}