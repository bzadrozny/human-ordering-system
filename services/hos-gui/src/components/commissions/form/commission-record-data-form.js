import React, {useState} from "react";
import {Button, Card, Col, Form, Row} from "react-bootstrap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const CommissionRecordDataForm = (props) => {
  let minStartDate = new Date()
  minStartDate.setDate(minStartDate.getDate() + 7)
  const isAdmin = props.isAdmin
  const record = props.record

  const [jobName, setJobName] = useState((record && record.jobName) || '')
  const [ordered, setOrdered] = useState((record && record.ordered) || null)
  const [wageRateMin, setWageRateMin] = useState((record && record.wageRateMin) || undefined)
  const [wageRateMax, setWageRateMax] = useState((record && record.wageRateMax) || undefined)
  const [startDate, setStartDate] = useState((record && new Date(record.startDate)) || minStartDate)
  const [endDate, setEndDate] = useState((record && record.endDate && new Date(record.endDate)) || undefined)
  const [settlementType, setSettlementType] = useState((record && record.settlementType.id) || 1)
  const [description, setDescription] = useState((record && record.description) || undefined)
  const [error, setError] = useState({
    jobName: '',
    ordered: '',
    wageRateMin: '',
    wageRateMax: '',
  })

  const validateField = (fields) => {
    let hasError = false
    const validation = {...error}
    fields.forEach(({field, value}) => {
      switch (field) {
        case "jobName":
          if (value == null || value.length < 5 || value.length > 20) {
            validation.jobName = "Pole jest wymagane i musi mieć od 5 do 20 znaków"
            hasError = true
          } else {
            validation.jobName = ''
          }
          break
        case "ordered":
          if (value == null || value < 1 || (isAdmin && value > 10000) || (!isAdmin && value > 200)) {
            validation.ordered = "Pole jest obowiązkowe i musi się mieścić od 1 do " + (isAdmin ? "10.000" : "200")
            hasError = true
          } else {
            validation.ordered = ''
          }
          break;
        case "wageRateMin":
          if (value == null || value === '' || value < 1 || value > 1000000) {
            validation.wageRateMin = "Pole jest obowiązkowe i musi się mieścić od 1 do 1.000.000"
            hasError = true
          } else {
            validation.wageRateMin = ''
          }
          break;
        case "wageRateMax":
          value = parseInt(value, 10)
          if (value == null || value === '' || value < 1 || value > 1000000 || value < wageRateMin) {
            validation.wageRateMax = "Pole jest obowiązkowe i musi się mieścić 1 do 1.000.000 i być większe od stawki minimalnej"
            hasError = true
          } else {
            validation.wageRateMax = ''
          }
          break;
        default:
          break;
      }
    })
    setError({...validation})
    return hasError
  }

  const handleSaveRecord = () => {
    let hasError = validateField([
      {field: 'jobName', value: jobName},
      {field: 'ordered', value: ordered},
      {field: 'wageRateMin', value: wageRateMin},
      {field: 'wageRateMax', value: wageRateMax}
    ])
    if (hasError) {
      return
    }
    setJobName('')
    setOrdered('')
    setWageRateMin('')
    setWageRateMax('')
    setStartDate(minStartDate)
    setEndDate(undefined)
    setDescription('')
    if (record !== null) {
      props.saveRecord({
        ...record,
        commission: props.commission,
        jobName,
        ordered,
        wageRateMin: wageRateMin === '' ? null : wageRateMin,
        wageRateMax: wageRateMax === '' ? null : wageRateMax,
        startDate: startDate != null ? startDate.toISOString().substr(0, 10) : undefined,
        endDate: endDate != null ? endDate.toISOString().substr(0, 10) : undefined,
        settlementType: {
          id: settlementType,
          desc: settlementType === 0 ? 'per godzina' : settlementType === 1 ? 'per miesiąc' : 'per zlecenie'
        },
        description: description === '' ? null : description
      })
    } else {
      props.saveRecord({
        commission: props.commission,
        jobName,
        ordered,
        wageRateMin: wageRateMin === '' ? null : wageRateMin,
        wageRateMax: wageRateMax === '' ? null : wageRateMax,
        startDate: startDate != null ? startDate.toISOString().substr(0, 10) : undefined,
        endDate: endDate != null ? endDate.toISOString().substr(0, 10) : undefined,
        settlementType: {
          id: settlementType,
          desc: settlementType === 0 ? 'per godzina' : settlementType === 1 ? 'per miesiąc' : 'per zlecenie'
        },
        description: description === '' ? null : description
      })
    }
  }

  return <Card className='commission-new-card mt-3'>
    <Form.Row>
      <Form.Group as={Col} controlId="formJobName">
        <Form.Label>Stanowisko</Form.Label>
        <Form.Control
            type='text'
            placeholder="Stanowisko"
            value={jobName}
            onChange={e => {
              const value = e.target.value
              validateField([{field: 'jobName', value}])
              setJobName(value)
            }}
        />
        <span style={{color: "red"}}>{error.jobName}</span>
      </Form.Group>
      <Form.Group as={Col} controlId="formOrdered">
        <Form.Label>Ilość</Form.Label>
        <Form.Control
            type='number'
            placeholder="Ilość"
            value={ordered}
            onChange={e => {
              const value = e.target.value
              validateField([{field: 'ordered', value}])
              setOrdered(value)
            }}
            min={1}
            max={props.isAdmin ? 10000 : 200}
            required
        />
        <span style={{color: "red"}}>{error.ordered}</span>
      </Form.Group>
    </Form.Row>

    <Form.Row>
      <Form.Group as={Col} controlId="formWageRateMin">
        <Form.Label>Pensja minimalna</Form.Label>
        <Form.Control
            type='number'
            placeholder="Pensja minimalna"
            value={wageRateMin}
            onChange={e => {
              const value = e.target.value
              validateField([{field: 'wageRateMin', value}])
              setWageRateMin(value)
            }}
            min={1}
            required
        />
        <span style={{color: "red"}}>{error.wageRateMin}</span>
      </Form.Group>
      <Form.Group as={Col} controlId="formWageRateMax">
        <Form.Label>Pensja maksymalna</Form.Label>
        <Form.Control
            type='number'
            placeholder="Pensja maksymalna"
            value={wageRateMax}
            onChange={e => {
              const value = e.target.value
              validateField([{field: 'wageRateMax', value}])
              setWageRateMax(value)
            }}
            min={1}
            required
        />
        <span style={{color: "red"}}>{error.wageRateMax}</span>
      </Form.Group>
    </Form.Row>

    <Form.Row>
      <Form.Group as={Col} controlId="formStartDate">
        <Form.Label>Data rozpoczęcia</Form.Label><br/>
        <Form.Control
            placeholder="Data rozpoczęcia"
            as={DatePicker}
            selected={startDate}
            onChange={date => {
              setStartDate(date)
              date > endDate && setEndDate(undefined)
            }}
            minDate={minStartDate}
            dateFormat='yyyy-MM-dd'
            required
        />
      </Form.Group>
      <Form.Group as={Col} controlId="formEndDate">
        <Form.Label>Data zakończenia</Form.Label><br/>
        <Form.Control
            placeholder="Data zakończenia"
            as={DatePicker}
            selected={endDate}
            onChange={date => setEndDate(date)}
            minDate={startDate}
            dateFormat='yyyy-MM-dd'
            required
        />
      </Form.Group>
    </Form.Row>

    <Form.Group as={Col} controlId="formSettlementType">
      <Form.Label>Typ rozliczenia</Form.Label>
      <Form.Control
          as='select'
          value={settlementType}
          onChange={e => setSettlementType(e.target.value)}
          required
      >
        <option value={0}>per godzina</option>
        <option value={1}>per miesiąc</option>
        <option value={2}>per zlecenie</option>
      </Form.Control>
    </Form.Group>

    <Form.Group as={Col} controlId="formDescription">
      <Form.Label>Uwagi</Form.Label>
      <Form.Control
          as='textarea'
          placeholder="Uwagi"
          value={description}
          onChange={e => setDescription(e.target.value)}
          maxLength={255}
      />
    </Form.Group>

    <Row>
      <Button onClick={() => handleSaveRecord()} variant='outline-info' className='mx-auto mt-3 mb-3 col-3'>
        {record ? 'Zatwierdź' : 'Dodaj rekord'}
      </Button>
    </Row>
  </Card>
}

export default CommissionRecordDataForm