import React, {Component} from 'react'
import {Link} from "react-router-dom";
import {OverlayTrigger, Table, Tooltip} from "react-bootstrap";
import {RiMoreFill, RiShareForwardFill} from "react-icons/ri";
import Toolbar from "../board/board-toolbar";
import CommissionsFilter from "./filter/commissions-filter";
import {CommissionAPI} from "../../api/hos-service-api";
import UserContext from "../../context/user-context";

class CommissionsBoard extends Component {
  static contextType = UserContext

  constructor(props) {
    super(props);
    this.state = {
      uploading: false,
      commissions: []
    }
  }

  componentDidMount = () => {
    this.uploadCommissions({})
  }

  componentDidUpdate(prevProps, prevState, snapshot) {
    const location = this.props.location
    const refresh = location.state ? location.state.refresh : false
    if (refresh) {
      this.uploadCommissions({})
      this.props.history.replace({
        pathname: '/board/commission',
        state: null
      })
    }
  }

  handleSubmitFilter = (filter) => {
    this.setState({
      uploading: true
    })
    this.uploadCommissions(filter)
  }

  uploadCommissions = (filter) => {
    CommissionAPI.allCommissions(filter)
        .then(resp => resp.data)
        .then(commissions =>
            this.setState({
              uploading: false,
              commissions: commissions
            })
        )
  }

  render() {
    const user = this.context
    const canAddCommission = user && user.authorities.some(auth => {
      return auth.id === 0 || auth.id === 4
    })

    const commissions = this.state.commissions.size ? (
        <tr>
          <td colSpan="4">Brak zamówień</td>
        </tr>
    ) : this.state.commissions.map((commission) => (
        <tr key={commission.id}>
          <td>{commission.id}</td>
          <td>{commission.orderDate}</td>
          <td>{commission.organisation}</td>
          <td>{commission.location}</td>
          <td>{commission.status.desc}</td>
          <td>
            <Link to={'/board/commission/' + commission.id}>
              <OverlayTrigger placement='bottom'
                              overlay={<Tooltip id="button-tooltip">Szczegóły zam. {commission.id}</Tooltip>}>
                <RiShareForwardFill/>
              </OverlayTrigger>
            </Link>
          </td>
        </tr>
    ))

    return (
        <>
          <Toolbar
              create={canAddCommission && (() => this.props.history.push('/board/commission/new'))}
          />
          <CommissionsFilter handleFilter={this.handleSubmitFilter}/>
          <div style={{marginLeft: '50px'}}>
            <Table className='col-md-11 my-3 mx-auto' size="sm" responsive="sm" striped hover>
              <thead>
              <tr>
                <th>ID</th>
                <th>Data zam.</th>
                <th>Organizacja</th>
                <th>Lokalizacja</th>
                <th>Status</th>
                <th><RiMoreFill/></th>
              </tr>
              </thead>
              <tbody>
              {commissions}
              </tbody>
            </Table>
          </div>
        </>
    )
  }
}

export default CommissionsBoard