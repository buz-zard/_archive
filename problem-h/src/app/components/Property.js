import React from 'react';
import PropTypes from 'prop-types';

import { toNonRoundedFixedString } from '../utils';

function Property({
  owner,
  address: { line1, line2, line3, line4, postCode, city, country },
  incomeGenerated,
}) {
  return (
    <section className="cf mt3 mb4">
      <div className="fl w-100 b pb2">
        <div className="fl w-25">Owner</div>
        <div className="fl w-50">Address</div>
        <div className="fl w-25">Generated Income</div>
      </div>
      <div className="fl w-100">
        <div className="fl w-25">{owner}</div>
        <div className="fl w-50">
          {[line1, line2, line3, line4, postCode, city, country]
            .filter(Boolean)
            .map(item => <div key={item}>{item}</div>)}
        </div>
        <div className="fl w-25">
          {toNonRoundedFixedString(incomeGenerated, 2)} £
        </div>
      </div>
    </section>
  );
}

Property.propTypes = {
  owner: PropTypes.string.isRequired,
  address: PropTypes.shape({
    line1: PropTypes.string.isRequired,
    line2: PropTypes.string,
    line3: PropTypes.string,
    line4: PropTypes.string.isRequired,
    postCode: PropTypes.string.isRequired,
    city: PropTypes.string.isRequired,
    country: PropTypes.string.isRequired,
  }).isRequired,
  incomeGenerated: PropTypes.number.isRequired,
};

export default Property;
